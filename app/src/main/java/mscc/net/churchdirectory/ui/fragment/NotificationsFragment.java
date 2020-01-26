package mscc.net.churchdirectory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mscc.net.churchdirectory.model.Notification;
import mscc.net.churchdirectory.room.MainDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.adapter.NotificationsAdapter;
import mscc.net.churchdirectory.connection.ConnectionManager;
import mscc.net.churchdirectory.room.dao.NotificationDao;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Dany on 03-01-2018.
 */

public class NotificationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NotificationsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Notification.Notification_> notifications;
    private MainDatabase db;
    private NotificationDao notificationDao;
    private Flowable<List<mscc.net.churchdirectory.room.model.Notification>> flowableNotifications;

    private Observable<Response<Notification>> getNotifications() {
        return ConnectionManager
                .getInstance()
                .getClient()
                .getNotifications()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        db = MainDatabase.getInstance(getActivity());
        notificationDao = db.getNotificationDao();

        notifications = new ArrayList<>();

        recyclerView = view.findViewById(R.id.rv_notifications);

        adapter = new NotificationsAdapter(notifications, getContext());
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        flowableNotifications = notificationDao.getFlowableNotifications();

        flowableNotifications
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataUpdated, this::onDataError);

        getNotifications().subscribe(this::setNotifications, this::setError);

        return view;
    }

    private void onDataError(Throwable throwable) {
        Log.e("RoomDB Error", throwable.getLocalizedMessage());
    }

    private void onDataUpdated(List<mscc.net.churchdirectory.room.model.Notification> notificationList) {
        notifications.clear();
        notifications.addAll(dbToList(notificationList));
        // adapter.notifyDataSetChanged();
        getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());

    }

    private Collection<? extends Notification.Notification_> dbToList(List<mscc.net.churchdirectory.room.model.Notification> notificationList) {
        List<Notification.Notification_> temp = new ArrayList<>();
        for (mscc.net.churchdirectory.room.model.Notification n : notificationList) {
            temp.add(new Notification.Notification_(String.valueOf(n.getId()), n.getTitle(), n.getMessage(), n.getImage(), n.getDate()));
        }
        return temp;
    }


    private void setError(Throwable throwable) {
        Log.e("notificationError", throwable.getLocalizedMessage());
    }

    private void setNotifications(Response<Notification> notificationResponse) {
        if (notificationResponse.isSuccessful()) {
            responseToDb(notificationResponse.body().getResponse().getNotifications());
        }
    }

    private void responseToDb(List<Notification.Notification_> _notifications) {
        List<mscc.net.churchdirectory.room.model.Notification> notificationList = new ArrayList<>();

        for (Notification.Notification_ n : _notifications) {
            notificationList.add(new mscc.net.churchdirectory.room.model.Notification(Integer.parseInt(n.getId()), n.getTitle(), n.getMessage(), n.getImage(), n.getDate()));
        }

        Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe(mainDatabase -> {
                    notificationDao.nukeTable();
                    notificationDao.insertAll(notificationList);
                }, err -> Log.e("RoomInsertError", err.getMessage()));
    }
}

package mscc.net.churchdirectory.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import mscc.net.churchdirectory.room.dao.ArticleDao;
import mscc.net.churchdirectory.room.dao.ContactDao;
import mscc.net.churchdirectory.room.dao.FamilyDao;
import mscc.net.churchdirectory.room.dao.MemberDao;
import mscc.net.churchdirectory.room.dao.NotificationDao;
import mscc.net.churchdirectory.room.model.Article;
import mscc.net.churchdirectory.room.model.Contact;
import mscc.net.churchdirectory.room.model.Family;
import mscc.net.churchdirectory.room.model.Member;
import mscc.net.churchdirectory.room.model.Notification;

/**
 * Created by Dany on 18-02-2018.
 */

@Database(entities = {Family.class, Contact.class, Member.class, Article.class, Notification.class}, version = 7, exportSchema = false)
public abstract class MainDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "SMCCDatabase";
    private static volatile MainDatabase INSTANCE;

    public abstract ContactDao getContactDao();

    public abstract FamilyDao getFamilyDao();

    public abstract MemberDao getMemberDao();

    public abstract ArticleDao getArticleDao();

    public abstract NotificationDao getNotificationDao();

    public static MainDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MainDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MainDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

package mscc.net.churchdirectory.ui.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import mscc.net.churchdirectory.room.MainDatabase;
import mscc.net.churchdirectory.ui.fragment.NotificationDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import mscc.net.churchdirectory.R;

import mscc.net.churchdirectory.room.model.Member;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static mscc.net.churchdirectory.ui.activity.UpdateProfileActivity.REGEX_DATE;

public class AddMemberActivity extends AppCompatActivity {

    public static final String EXTRA_FAMILY_ID = "extra_family_id";
    public static final String EXTRA_MEMBER_ID = "extra_member_id";

    public static final String REGEX_BLOOD_GROUP = "(A|B|AB|O)[+-]";

    private int familyId = -1;
    private int memberId = 0;

    private Spinner etRelation;
    private TextView etDob;
    private Spinner etBloodGroup;
    private EditText etName;
    private Button cancel;
    private Button delete;
    private Button save;

    private MainDatabase mainDatabase;
    private Member member;

    private Boolean isDobValid = false;
    private Boolean isRelationValid = false;
    private Boolean isBloodValid = false;

    private List<String> relations = new ArrayList<>();
    private List<String> bloodGroups = new ArrayList<>();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        relations.add("");
        relations.add("Family Head");
        relations.add("Father");
        relations.add("Mother");
        relations.add("Wife");
        relations.add("Husband");
        relations.add("Son");
        relations.add("Daughter");
        relations.add("Brother");
        relations.add("Sister");
        relations.add("Grandfather");
        relations.add("Grandmother");
        relations.add("Grand son");
        relations.add("Grand daughter");
        relations.add("Uncle");
        relations.add("Aunt");
        relations.add("Son-in-law");
        relations.add("Daughter-in-law");
        relations.add("Relative");

        bloodGroups.add("");
        bloodGroups.add("O-");
        bloodGroups.add("O+");
        bloodGroups.add("A+");
        bloodGroups.add("A-");
        bloodGroups.add("B-");
        bloodGroups.add("B+");
        bloodGroups.add("AB-");
        bloodGroups.add("AB+");

        ArrayAdapter<String> relationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, relations);
        ArrayAdapter<String> bloodGroupAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bloodGroups);

        mainDatabase = MainDatabase.getInstance(this);
        member = new Member(0, 0, "", "", "", "");

        etRelation = findViewById(R.id.member_relation);
        etDob = findViewById(R.id.member_dob);
        etBloodGroup = findViewById(R.id.member_blood_group);
        etName = findViewById(R.id.member_name);
        cancel = findViewById(R.id.button_member_cancel);
        delete = findViewById(R.id.button_member_delete);
        save = findViewById(R.id.button_member_save);

        etRelation.setAdapter(relationAdapter);
        etBloodGroup.setAdapter(bloodGroupAdapter);

        if (getIntent().getExtras() != null) {
            familyId = getIntent().getIntExtra(EXTRA_FAMILY_ID, -1);
            memberId = getIntent().getIntExtra(EXTRA_MEMBER_ID, 0);
        }

        if (familyId == -1) {
            finish();
        }

        if (memberId == 0) {
            delete.setVisibility(View.GONE);
        } else {
            mainDatabase.getMemberDao().getMember(memberId).subscribe(this::onDataUpdated, this::onDateError);
        }

        delete.setOnClickListener(v -> {
            setMembers();
            Observable.just(mainDatabase)
                    .subscribeOn(Schedulers.io())
                    .subscribe(db -> db.getMemberDao().delete(member));
            finish();
        });
        cancel.setOnClickListener(v -> finish());
        save.setOnClickListener(v -> {
            if (isDobValid) {
                setMembers();
                Observable.just(mainDatabase)
                        .subscribeOn(Schedulers.io())
                        .subscribe(db -> db.getMemberDao()
                                .insert(member));
                finish();
            } else {

                if (!isDobValid) {
                    etDob.setError("Use picker");
                }

                NotificationDialogFragment dialog = NotificationDialogFragment.newInstance("There are errors that needs to be fixed");
                dialog.show(getFragmentManager(), "notification");
            }
        });

        etDob.setOnClickListener(v -> showDatePicker());

        etDob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isDobValid = Pattern.matches(REGEX_DATE, s);
                if (isDobValid) {
                    etDob.setError(null);
                } else {
                    etDob.setError("Pattern doesn't match");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private Boolean validateRelation(String s) {
        Boolean valid = false;
        if (s.length() > 0 && relations.contains(s)) {
            valid = true;
        }
        return valid;
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("DefaultLocale") DatePickerDialog dpd = new DatePickerDialog(this,
                (datePicker, year, month, day) -> {
                    etDob.setText(String.format("%d %s", day, getMonth(month)));
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    private String getMonth(int month) {
        month += 1;
        String monthString;
        switch (month) {
            case 1:
                monthString = "Jan";
                break;
            case 2:
                monthString = "Feb";
                break;
            case 3:
                monthString = "Mar";
                break;
            case 4:
                monthString = "Apr";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "Jun";
                break;
            case 7:
                monthString = "Jul";
                break;
            case 8:
                monthString = "Aug";
                break;
            case 9:
                monthString = "Sep";
                break;
            case 10:
                monthString = "Oct";
                break;
            case 11:
                monthString = "Nov";
                break;
            case 12:
                monthString = "Dec";
                break;
            default:
                monthString = "ERR";
                break;
        }
        return monthString;
    }


    private void setMembers() {
        member.setId(memberId);
        member.setFamilyId(familyId);
        member.setRelation(etRelation.getSelectedItem().toString());
        member.setBloodGroup(etBloodGroup.getSelectedItem().toString());
        member.setDob(etDob.getText().toString());
        member.setName(etName.getText().toString());
        Log.e("save memberId", String.valueOf(member.getId()));
        Log.e("save familyId", String.valueOf(member.getFamilyId()));
    }

    private void onDateError(Throwable throwable) {
        Log.e("AddMember", throwable.getLocalizedMessage());
    }

    private int getRelationId(String pg) {
        int pId = 0;

        for (int i = 0; i < relations.size(); i++) {
            if (pg.equals(relations.get(i))) {
                pId = i;
            }
        }

        return pId;
    }

    private int getBloodGroupId(String pg) {
        int pId = 0;

        for (int i = 0; i < bloodGroups.size(); i++) {
            if (pg.equals(bloodGroups.get(i))) {
                pId = i;
            }
        }

        return pId;
    }

    private void onDataUpdated(Member member) {
        Log.e("load memberId", String.valueOf(member.getId()));
        Log.e("load familyId", String.valueOf(member.getFamilyId()));
        etName.setText(member.getName());
//        etRelation.setText(member.getRelation());
        etRelation.setSelection(getRelationId(member.getRelation()));
        etBloodGroup.setSelection(getBloodGroupId(member.getBloodGroup()));
        etDob.setText(member.getDob());
        isDobValid = Pattern.matches(REGEX_DATE, member.getDob());
        isBloodValid = Pattern.matches(REGEX_BLOOD_GROUP, member.getBloodGroup());

        if (isDobValid) {
            etDob.setError(null);
        } else {
            etDob.setError("Invalid pattern. Use picker");
        }
    }
}

package com.example.dwayne.ifocus.study.history;

/**
 * Created by Subin on 5/1/2016.
 */
public class StudyHistoryDao {
    private int _id;
    private String completionDate;
    private boolean completionStatus;

    public StudyHistoryDao(int _id, String CompletionDate, boolean completionStatus) {
        this._id = _id;
        this.completionDate = CompletionDate;
        this.completionStatus = completionStatus;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public boolean isCompletionStatus() {
        return completionStatus;
    }

    public void setCompletionStatus(boolean completionStatus) {
        this.completionStatus = completionStatus;
    }

}

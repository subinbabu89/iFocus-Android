package com.example.dwayne.ifocus.study.listener;

import com.example.dwayne.ifocus.study.pojo.Study;

/**
 * Created by Subin on 4/9/2016.
 */
public interface StudyEditClickListener {

    void onDeleteClicked(Study study);
    void onEditClicked(Study study);
    void markChanged();
}

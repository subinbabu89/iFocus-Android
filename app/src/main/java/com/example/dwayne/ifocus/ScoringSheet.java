package com.example.dwayne.ifocus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Subin on 4/30/2016.
 */
public class ScoringSheet {

    public static final String HEALTH_TASK_DONE = "HEALTH_TASK_DONE";
    private static final int HEALTH_TASK_DONE_POINTS = 10;

    public static final String STUDY_TASK_DONE = "STUDY_TASK_DONE";
    private static final int STUDY_TASK_DONE_POINTS = 10;

    private static Map<String,Integer> score = new HashMap<>();

    public static final Map<String,Integer> getScoreSheet(){
        return score;
    }

    public ScoringSheet(){
        score.put(HEALTH_TASK_DONE,HEALTH_TASK_DONE_POINTS);
        score.put(STUDY_TASK_DONE,STUDY_TASK_DONE_POINTS);
    }

}

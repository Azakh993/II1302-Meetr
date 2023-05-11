package com.group7.meetr.data.model;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class Consensus {
    private static final PublishSubject<ArrayList<Object>> consensusAgreedSubject = PublishSubject.create();
    private static final PublishSubject<ArrayList<Object>> consensusNotSureSubject = PublishSubject.create();

    public static void setConsensusAgreedSubject(ArrayList<Object> agreedArrayList) {
        consensusAgreedSubject.onNext(agreedArrayList);
    }

    public static void setConsensusNotSureSubject(ArrayList<Object> notSureArrayList) {
        consensusNotSureSubject.onNext(notSureArrayList);
    }

    /**
     * Observe this is if you need the people who have agreed in the current consensusState.
     * @return returns an array of objects
     */
    public static Observable<ArrayList<Object>> getConsensusAgreedSubject() {
        return consensusAgreedSubject;
    }
    /**
     * Observe this is if you need the people who have not agreed in the current consensusState.
     * @return returns an array of objects
     */
    public static Observable<ArrayList<Object>> getConsensusNotSureSubject() {
        return consensusNotSureSubject;
    }
}
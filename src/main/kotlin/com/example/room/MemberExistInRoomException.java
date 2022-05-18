package com.example.room;

public class MemberExistInRoomException extends Exception {
    MemberExistInRoomException(){
        super("This member already joined room.");
    }
}

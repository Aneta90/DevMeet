package pl.com.devmeet.devmeet.domain.group_meeting;

enum MeetingStatus {

    WAITING_FOR_MEETING(""),
    WAITING_FOR_MEMBERS(""),
    COMPLETED(""),
    CANCELLED("");

    private String status;
    private String statusErr = "Wrong argument";

    MeetingStatus(String status) {
        this.status = status;
    }

    public String saveStatusToDatabase(String status) {
        if (status.equals(MeetingStatus.WAITING_FOR_MEETING.toString()))
            return "waiting";
        else if (status.equals(MeetingStatus.WAITING_FOR_MEMBERS.toString()))
            return "members";
        else if (status.equals(MeetingStatus.COMPLETED.toString()))
            return "complete";
        else if (status.equals(MeetingStatus.CANCELLED.toString()))
            return "cancel";
        else
            throw new IllegalArgumentException(statusErr);
    }

    @Override
    public String toString() {
        return status;
    }
}

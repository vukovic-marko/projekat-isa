package isa.projekat.model.Forms;

public class NewFlightForm {

    private Long airlineCompanyId;

    //startpoint
    private String startPointCity;
    private String startPointState;

    //endpoint
    private String endPointState;
    private String endPointCity;

    //date
    private String startDate;
    private String endDate;

    //airplane
    private Long airplaneId;

    public Long getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getAirlineCompanyId() {
        return airlineCompanyId;
    }

    public void setAirlineCompanyId(Long airlineCompanyId) {
        this.airlineCompanyId = airlineCompanyId;
    }

    public String getStartPointCity() {
        return startPointCity;
    }

    public void setStartPointCity(String startPointCity) {
        this.startPointCity = startPointCity;
    }

    public String getStartPointState() {
        return startPointState;
    }

    public void setStartPointState(String startPointState) {
        this.startPointState = startPointState;
    }

    public String getEndPointState() {
        return endPointState;
    }

    public void setEndPointState(String endPointState) {
        this.endPointState = endPointState;
    }

    public String getEndPointCity() {
        return endPointCity;
    }

    public void setEndPointCity(String endPointCity) {
        this.endPointCity = endPointCity;
    }

}

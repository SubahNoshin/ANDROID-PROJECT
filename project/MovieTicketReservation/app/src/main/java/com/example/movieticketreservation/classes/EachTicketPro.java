package com.example.movieticketreservation.classes;

public class EachTicketPro extends EachTicket{
    private String seat;

    public EachTicketPro(String seat) {
        this.seat = seat;
    }

    public EachTicketPro(String ticketId, String theatreName, String movieName, String date, String time, String image, String price, String seat) {
        super(ticketId, theatreName, movieName, date, time, image, price);
        this.seat = seat;
    }
    public EachTicketPro(String seat, EachTicket eachTicket){
        super(eachTicket);
        this.seat = seat;
    }
}

package com.example.movieticketreservation.classes;

import java.io.Serializable;

public class EachTicket implements Serializable {
    private String ticketId, theatreName, movieName, date, time, image, price;

    public EachTicket() {
    }

    public EachTicket(EachTicket eachTicket){
        this.ticketId = eachTicket.ticketId;
        this.theatreName = eachTicket.theatreName;
        this.movieName = eachTicket.movieName;
        this.date = eachTicket.date;
        this.time = eachTicket.time;
        this.image = eachTicket.image;
        this.price = eachTicket.price;
    }

    public EachTicket(String ticketId, String theatreName, String movieName, String date, String time, String image,String price) {
        this.ticketId = ticketId;
        this.theatreName = theatreName;
        this.movieName = movieName;
        this.date = date;
        this.time = time;
        this.image = image;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

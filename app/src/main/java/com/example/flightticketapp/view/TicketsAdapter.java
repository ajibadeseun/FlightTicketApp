package com.example.flightticketapp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.flightticketapp.R;
import com.example.flightticketapp.network.model.Ticket;
import com.github.ybq.android.spinkit.SpinKitView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.MyViewHolder> {
   private Context context;
   private List<Ticket> contactList;
   private TicketsAdapterListener listener;

    public TicketsAdapter(Context context, List<Ticket> contactList, TicketsAdapterListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ticket_row,viewGroup,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Ticket ticket = contactList.get(i);

        Glide.with(context)
                .load(ticket.getAirline().getLogo())
                .apply(RequestOptions.circleCropTransform())
                .into(myViewHolder.logo);

        myViewHolder.airlineName.setText(ticket.getAirline().getName());

        myViewHolder.departure.setText(ticket.getDeparture() + " Dep");
        myViewHolder.arrival.setText(ticket.getArrival() + " Dest");

        myViewHolder.duration.setText(ticket.getFlightNumber());
        myViewHolder.duration.append(", " + ticket.getDuration());
        myViewHolder.stops.setText(ticket.getNumberOfStops() + " Stops");


        if(!TextUtils.isEmpty(ticket.getInstructions())){
            myViewHolder.duration.append(", " + ticket.getInstructions());
        }

        if(ticket.getPrice() != null){
           myViewHolder.price.setText("₦" + String.format("%.0f",ticket.getPrice().getPrice()));
           myViewHolder.seats.setText(ticket.getPrice().getSeats() + " Seats");
           myViewHolder.loader.setVisibility(View.INVISIBLE);
        }
        else{
            myViewHolder.loader.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.airline_name)
        TextView airlineName;

        @BindView(R.id.logo)
        ImageView logo;

        @BindView(R.id.number_of_stops)
        TextView stops;

        @BindView(R.id.number_of_seats)
        TextView seats;

        @BindView(R.id.departure)
        TextView departure;

        @BindView(R.id.arrival)
        TextView arrival;

        @BindView(R.id.duration)
        TextView duration;

        @BindView(R.id.price)
        TextView price;

        @BindView(R.id.loader)
        SpinKitView loader;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(v ->{
             listener.onTicketSelected(contactList.get(getAdapterPosition()));
            });
        }
    }

    public interface TicketsAdapterListener{
        void onTicketSelected(Ticket contact);
    }
}

package com.stocktracker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stocktracker.data.Quote;
import com.stocktracker.util.FormatUtils;
import com.stocktracker.util.Utils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by dlarson on 10/27/15.
 */
public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {
    private static final String TAG = StockAdapter.class.getSimpleName();

    private List<Quote> quotes;

    private final LayoutInflater layoutInflater;
    private final WeakReference<Context> context;

    public StockAdapter(Context context, List<Quote> quotes) {
        this.context = new WeakReference<>(context);
        this.quotes = quotes;
        layoutInflater = LayoutInflater.from(context);

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.stock_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Quote quote = quotes.get(position);

        holder.getStockSymbol().setText(quote.getSymbol());

        int changeColor = getChangeColor(quote);
        final FormatUtils.ChangeType changeType = FormatUtils.getChangeType(quote.getChange());
        final String changeSymbol = FormatUtils.getChangeSymbol(layoutInflater.getContext(), changeType);

        holder.getStockSymbol().setText(quote.getSymbol());
        holder.getStockName().setText(quote.getName());

        String lastTradePriceFormatted = FormatUtils.formatCurrency(quote.getLastTradePriceOnly());
        holder.getStockPrice().setText(lastTradePriceFormatted);

        String changeFormatted = FormatUtils.formatCurrency(quote.getChange());
        holder.getStockChange().setText(changeFormatted);

        double changePercent = FormatUtils.getPercentageChange(quote.getLastTradePriceOnly(), quote.getChange());
        String changePercentFormatted = FormatUtils.formatPercent(changePercent);
        holder.getStockChangePercentage().setText(changeSymbol + changePercentFormatted);

        holder.getStockChange().setTextColor(changeColor);
        holder.getStockChangePercentage().setTextColor(changeColor);
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    private int getChangeColor(Quote quote) {
        int changeColor;
        final FormatUtils.ChangeType changeType = FormatUtils.getChangeType(quote.getChange());

        if (changeType == FormatUtils.ChangeType.Negative) {
            if(Utils.hasMarshmallow()) {
                changeColor = layoutInflater.getContext().getResources().getColor(R.color.red, context.get().getTheme());
            } else {
                changeColor = layoutInflater.getContext().getResources().getColor(R.color.red);
            }
        } else {
            if(Utils.hasMarshmallow()) {
                changeColor = layoutInflater.getContext().getResources().getColor(R.color.green, context.get().getTheme());
            } else {
                changeColor = layoutInflater.getContext().getResources().getColor(R.color.green);
            }
        }
        return changeColor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView stockSymbol;
        private TextView stockName;
        private TextView stockPrice;
        private TextView stockChange;
        private TextView stockChangePercentage;

        public ViewHolder(View itemView) {
            super(itemView);

            stockSymbol = (TextView) itemView.findViewById(R.id.stockSymbol);
            stockName = (TextView) itemView.findViewById(R.id.stockName);
            stockPrice = (TextView) itemView.findViewById(R.id.stockPrice);
            stockChange = (TextView) itemView.findViewById(R.id.stockChange);
            stockChangePercentage = (TextView) itemView.findViewById(R.id.stockChangePercentage);
        }

        public TextView getStockSymbol() {
            return stockSymbol;
        }

        public TextView getStockName() {
            return stockName;
        }

        public TextView getStockPrice() {
            return stockPrice;
        }

        public TextView getStockChange() {
            return stockChange;
        }

        public TextView getStockChangePercentage() {
            return stockChangePercentage;
        }
    }
}

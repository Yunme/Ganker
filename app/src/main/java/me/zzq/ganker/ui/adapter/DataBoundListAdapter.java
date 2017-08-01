package me.zzq.ganker.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zzq in 2017/7/18
 * <p>
 * A generic RecyclerView Adapter that uses Data Binding & DiffUtil.
 * <p>
 * Support MultiTpe Item. use one-to-one POJO and ViewDataBinding.
 **/

public class DataBoundListAdapter extends RecyclerView.Adapter<DataBoundViewHolder> {

    public SimpleArrayMap<Class<?>, ItemBindingProvider> viewDataBinding = new SimpleArrayMap<>();

    @Nullable
    private List items;
    /**
     * each time data is set, we update this variable so that if DiffUtil calculation returns
     * after repetitive updates, we can ignore the old calculation.
     */
    private int dataVersion = 0;

    @Override
    public DataBoundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemBindingProvider viewBinding = viewDataBinding.valueAt(viewType);
        return new DataBoundViewHolder<>(viewBinding.createBinding(parent));
    }


    @Override
    public void onBindViewHolder(DataBoundViewHolder holder, int position) {
        ItemBindingProvider viewBinding = viewDataBinding.valueAt(getItemViewType(position));
        viewBinding.onBind(holder.binding, items.get(position), position);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        return viewDataBinding.indexOfKey(items.get(position).getClass());
    }

    @SuppressWarnings("unchecked")
    public void addItems(@Nullable List itemList) {
        if (this.items == null) {
            this.items = itemList;
            notifyDataSetChanged();
        } else if (itemList != null) {
            this.items.addAll(itemList);
            notifyItemRangeInserted(items.size() - itemList.size() , itemList.size());
        }
    }

    @SuppressWarnings("unchecked")
    public void addItem(@Nullable Object object) {
        if (items == null) {
            this.items = new ArrayList();
            this.items.add(object);
            notifyDataSetChanged();
        } else {
            this.items.add(object);
            notifyItemInserted(items.size());
        }
    }

    public void setItems(@Nullable List items) {
        this.items = items;
        notifyDataSetChanged();
    }

    /* @SuppressLint("StaticFieldLeak")
    @MainThread
    public void replace(final List<T> update) {
        dataVersion++;
        if (items == null) {
            if (update == null)
                return;
            this.items = update;
            notifyDataSetChanged();
        } else if (update == null) {
            int oldSize = items.size();
            items = null;
            notifyItemRangeRemoved(0, oldSize);
        } else {
            final int startVersion = dataVersion;
            final List<T> oldItems = items;
            new AsyncTask<Void, Void, DiffUtil.DiffResult>() {

                @Override
                protected DiffUtil.DiffResult doInBackground(Void... voids) {
                    return DiffUtil.calculateDiff(new DiffUtil.Callback() {
                        @Override
                        public int getOldListSize() {
                            return oldItems.size();
                        }

                        @Override
                        public int getNewListSize() {
                            return update.size();
                        }

                        @Override
                        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                            T oldItem = oldItems.get(oldItemPosition);
                            T newItem = update.get(newItemPosition);
                            return DataBoundListAdapter.this.areItemsTheSame(oldItem, newItem);
                        }

                        @Override
                        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                            T oldItem = oldItems.get(oldItemPosition);
                            T newItem = update.get(newItemPosition);
                            return DataBoundListAdapter.this.areContentsTheSame(oldItem, newItem);
                        }
                    });
                }

                @Override
                protected void onPostExecute(DiffUtil.DiffResult diffResult) {
                    if (startVersion != dataVersion) {
                        //ignore update
                        return;
                    }
                    items = update;
                    diffResult.dispatchUpdatesTo(DataBoundListAdapter.this);
                }
            }.execute();
        }
    }*/


  /*  protected abstract boolean areItemsTheSame(T oldItem, T newItem);

    protected abstract boolean areContentsTheSame(T oldItem, T newItem);*/


    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}

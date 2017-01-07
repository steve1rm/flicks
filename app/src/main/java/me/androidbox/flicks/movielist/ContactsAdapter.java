package me.androidbox.flicks.movielist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import me.androidbox.flicks.R;
import me.androidbox.flicks.model.Contact;
import me.androidbox.flicks.moviedetail.DetailFragment;
import me.androidbox.flicks.moviedetail.DetailsActivity;
import me.androidbox.flicks.moviedetail.MovieDetailActivity;
import me.androidbox.flicks.moviedetail.MovieDetailView;

// Provide the underlying view for an individual list item.
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.VH> {
    private Activity mContext;
    private List<Contact> mContacts;

    public ContactsAdapter(Activity context, List<Contact> contacts) {
        mContext = context;
        if (contacts == null) {
            throw new IllegalArgumentException("contacts must not be null");
        }
        mContacts = contacts;
    }

    // Inflate the view based on the viewType provided.
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new VH(itemView, mContext);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(final VH holder, int position) {

        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.ivProfile.setImageBitmap(bitmap);
                Palette palette = Palette.from(bitmap).generate();
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                if(vibrant != null) {
                    holder.vPalette.setBackgroundColor(vibrant.getRgb());
                    holder.tvName.setTextColor(vibrant.getTitleTextColor());
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        Contact contact = mContacts.get(position);
        holder.rootView.setTag(contact);
        holder.tvName.setText(contact.getName());
        holder.ivProfile.setTag(target);

        Picasso.with(mContext).load(contact.getThumbnailDrawable()).into(target);
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    // Provide a reference to the views for each contact item
    public final static class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView ivProfile;
        final TextView tvName;
        final View vPalette;

        public VH(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            ivProfile = (ImageView)itemView.findViewById(R.id.ivProfile);
            tvName = (TextView)itemView.findViewById(R.id.tvName);
            vPalette = itemView.findViewById(R.id.vPalette);

            // Navigate to contact details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Contact contact = (Contact)v.getTag();
                    if (contact != null) {
                        // Fire an intent when a contact is selected
                        // Pass contact object in the bundle and populate details activity.

                        final Intent intent = new Intent(context, DetailsActivity.class);
/*
                        intent.putExtra(MovieDetailView.EXTRA_CONTACT, contact);

                        Pair<View, String> pair1 = Pair.create((View)ivProfile, "image");
                        Pair<View, String> pair2 = Pair.create(vPalette, "palette");
                        Pair<View, String> pair3 = Pair.create((View)tvName, "name");
*/

                        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat
                                        .makeSceneTransitionAnimation(
                                                (Activity)context,
                                                ivProfile,
                                                context.getString(R.string.image_transition));

                        context.startActivity(intent, optionsCompat.toBundle());
                    }
                }
            });
        }
    }
}

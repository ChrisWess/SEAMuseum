package com.seamuseum.auswahlelement.werke;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seamuseum.auswahlelement.R;
import com.seamuseum.auswahlelement.comments.EntriesActivity;
import com.seamuseum.auswahlelement.comments.WriteEntryActivity;


public class WerkeMainActivity extends Activity {

    private RecyclerView _werkeList;

    private DatabaseReference _database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_werke_main);

        activity = this;

        _werkeList = (RecyclerView) findViewById(R.id.werke_list);
        _werkeList.setHasFixedSize(true);
        _werkeList.setLayoutManager(new LinearLayoutManager(this));

        _database = FirebaseDatabase.getInstance().getReference().child(
                getApplicationContext().getString(R.string.werkeDbRef));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Werk, WerkViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Werk, WerkViewHolder>(
                Werk.class,
                R.layout.werk_row,
                WerkViewHolder.class,
                _database
        ) {
            @Override
            protected void populateViewHolder(WerkViewHolder viewHolder, Werk model, int position) {
                viewHolder.setTitle(model.getTitel());
                viewHolder.setDesc(model.getBeschreibung());
                viewHolder.setImage(getApplicationContext(), model.getBildUrl(), model.getTitel());
            }
        };
        _werkeList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class WerkViewHolder extends RecyclerView.ViewHolder{

        View _view;

        public WerkViewHolder(View itemView) {
            super(itemView);
            _view = itemView;
        }

        public void setTitle(String title)
        {
            TextView werkTitle = (TextView) _view.findViewById(R.id.werk_title);
            werkTitle.setText(title);
        }

        public void setDesc(String desc)
        {
            TextView werkDesc = (TextView) _view.findViewById(R.id.werk_desc);
            werkDesc.setText(desc);
        }

        public void setImage(final Context ctx, String image, final String key) {
            ImageView werkImage = (ImageView) _view.findViewById(R.id.werk_image);
            Glide.with(ctx)
                    .load(image)
                    .into(werkImage);

            //imon
            ImageButton button = (ImageButton) _view.findViewById(R.id.addCommentWerk);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EntriesActivity.key=key;
                    activity.startActivity(new Intent(ctx, EntriesActivity.class));
                }
            });
        }
    }

    private static Activity activity;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.werke_main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add)
        {
            startActivity(new Intent(getApplicationContext(), WerkActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}


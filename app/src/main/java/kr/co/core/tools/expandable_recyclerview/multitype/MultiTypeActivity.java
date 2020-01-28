package kr.co.core.tools.expandable_recyclerview.multitype;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import kr.co.core.tools.R;

import static kr.co.core.tools.expandable_recyclerview.GenreDataFactory.makeGenres;


public class MultiTypeActivity extends AppCompatActivity {

  private MultiTypeGenreAdapter adapter;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_multi_type);
//    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    getSupportActionBar().setTitle(getClass().getSimpleName());

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    adapter = new MultiTypeGenreAdapter(makeGenres());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    adapter.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    adapter.onRestoreInstanceState(savedInstanceState);
  }
}

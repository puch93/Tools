package kr.co.core.tools.expandable_recyclerview.singlecheck;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablecheckrecyclerview.CheckableChildRecyclerViewAdapter;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import java.util.List;

import kr.co.core.tools.R;
import kr.co.core.tools.expandable_recyclerview.Artist;
import kr.co.core.tools.expandable_recyclerview.expand.GenreViewHolder;

public class SingleCheckGenreAdapter extends
        CheckableChildRecyclerViewAdapter<GenreViewHolder, SingleCheckArtistViewHolder> {

  public SingleCheckGenreAdapter(List<SingleCheckGenre> groups) {
    super(groups);
  }

  @Override
  public SingleCheckArtistViewHolder onCreateCheckChildViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_singlecheck_arist, parent, false);
    return new SingleCheckArtistViewHolder(view);
  }

  @Override
  public void onBindCheckChildViewHolder(SingleCheckArtistViewHolder holder, int position,
      CheckedExpandableGroup group, int childIndex) {
    final Artist artist = (Artist) group.getItems().get(childIndex);
    holder.setArtistName(artist.getName());
  }

  @Override
  public GenreViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_item_genre, parent, false);
    return new GenreViewHolder(view);
  }

  @Override
  public void onBindGroupViewHolder(GenreViewHolder holder, int flatPosition,
      ExpandableGroup group) {
    holder.setGenreTitle(group);
  }
}

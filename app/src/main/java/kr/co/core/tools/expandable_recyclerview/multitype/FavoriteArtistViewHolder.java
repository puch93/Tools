package kr.co.core.tools.expandable_recyclerview.multitype;

import android.view.View;
import android.widget.TextView;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import kr.co.core.tools.R;

public class FavoriteArtistViewHolder extends ChildViewHolder {

  private TextView favoriteArtistName;

  public FavoriteArtistViewHolder(View itemView) {
    super(itemView);
    favoriteArtistName = (TextView) itemView.findViewById(R.id.list_item_favorite_artist_name);
  }

  public void setArtistName(String name) {
    favoriteArtistName.setText(name);
  }

}

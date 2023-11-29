// Generated by view binder compiler. Do not edit!
package com.example.diyhomeautomation.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.diyhomeautomation.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CustomRestrictionCardviewBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final Switch cardRestrictionActiveSw;

  @NonNull
  public final TextView cardRestrictionTitleTv;

  @NonNull
  public final TextView cardRestrictionTotalTv;

  private CustomRestrictionCardviewBinding(@NonNull CardView rootView,
      @NonNull Switch cardRestrictionActiveSw, @NonNull TextView cardRestrictionTitleTv,
      @NonNull TextView cardRestrictionTotalTv) {
    this.rootView = rootView;
    this.cardRestrictionActiveSw = cardRestrictionActiveSw;
    this.cardRestrictionTitleTv = cardRestrictionTitleTv;
    this.cardRestrictionTotalTv = cardRestrictionTotalTv;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static CustomRestrictionCardviewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CustomRestrictionCardviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.custom_restriction_cardview, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CustomRestrictionCardviewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardRestriction_active_sw;
      Switch cardRestrictionActiveSw = ViewBindings.findChildViewById(rootView, id);
      if (cardRestrictionActiveSw == null) {
        break missingId;
      }

      id = R.id.cardRestriction_title_tv;
      TextView cardRestrictionTitleTv = ViewBindings.findChildViewById(rootView, id);
      if (cardRestrictionTitleTv == null) {
        break missingId;
      }

      id = R.id.cardRestriction_total_tv;
      TextView cardRestrictionTotalTv = ViewBindings.findChildViewById(rootView, id);
      if (cardRestrictionTotalTv == null) {
        break missingId;
      }

      return new CustomRestrictionCardviewBinding((CardView) rootView, cardRestrictionActiveSw,
          cardRestrictionTitleTv, cardRestrictionTotalTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

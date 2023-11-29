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

public final class CustomScheduleCardviewBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final Switch cardScheduleActiveSw;

  @NonNull
  public final TextView cardScheduleDayTv;

  @NonNull
  public final TextView cardScheduleTimeTv;

  @NonNull
  public final TextView cardScheduleTitleTv;

  @NonNull
  public final TextView cardScheduleTotalTv;

  private CustomScheduleCardviewBinding(@NonNull CardView rootView,
      @NonNull Switch cardScheduleActiveSw, @NonNull TextView cardScheduleDayTv,
      @NonNull TextView cardScheduleTimeTv, @NonNull TextView cardScheduleTitleTv,
      @NonNull TextView cardScheduleTotalTv) {
    this.rootView = rootView;
    this.cardScheduleActiveSw = cardScheduleActiveSw;
    this.cardScheduleDayTv = cardScheduleDayTv;
    this.cardScheduleTimeTv = cardScheduleTimeTv;
    this.cardScheduleTitleTv = cardScheduleTitleTv;
    this.cardScheduleTotalTv = cardScheduleTotalTv;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static CustomScheduleCardviewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CustomScheduleCardviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.custom_schedule_cardview, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CustomScheduleCardviewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cardSchedule_active_sw;
      Switch cardScheduleActiveSw = ViewBindings.findChildViewById(rootView, id);
      if (cardScheduleActiveSw == null) {
        break missingId;
      }

      id = R.id.cardSchedule_day_tv;
      TextView cardScheduleDayTv = ViewBindings.findChildViewById(rootView, id);
      if (cardScheduleDayTv == null) {
        break missingId;
      }

      id = R.id.cardSchedule_time_tv;
      TextView cardScheduleTimeTv = ViewBindings.findChildViewById(rootView, id);
      if (cardScheduleTimeTv == null) {
        break missingId;
      }

      id = R.id.cardSchedule_title_tv;
      TextView cardScheduleTitleTv = ViewBindings.findChildViewById(rootView, id);
      if (cardScheduleTitleTv == null) {
        break missingId;
      }

      id = R.id.cardSchedule_total_tv;
      TextView cardScheduleTotalTv = ViewBindings.findChildViewById(rootView, id);
      if (cardScheduleTotalTv == null) {
        break missingId;
      }

      return new CustomScheduleCardviewBinding((CardView) rootView, cardScheduleActiveSw,
          cardScheduleDayTv, cardScheduleTimeTv, cardScheduleTitleTv, cardScheduleTotalTv);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

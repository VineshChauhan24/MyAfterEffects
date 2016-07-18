
package model;

import de.jaret.util.date.Interval;
import de.jaret.util.ui.timebars.model.TimeBarModel;
import de.jaret.util.ui.timebars.model.TimeBarRow;
import framework.Application;
import framework.Model;
import manager.Timeline;
import manager.Video;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.function.Consumer;

// Framework

public final class TimelineModel extends Model {
  private Collection<Timeline> timelines = new LinkedHashSet<>();

  public TimelineModel(final Application application) {
    super(application);
    this.on("video:new", (Video v) -> add(v));
  }

  public Timeline[] timelines() {
    return this.timelines.toArray(new Timeline[this.timelines.size()]);
  }

  public static ArrayList<Video> GetVideosAtFrame(int frame_nb) {
    ArrayList<Video> list = new ArrayList<>();
    forEachInterval(null, new Consumer<Interval>() {
      @Override
      public void accept(Interval interval) {
        // TODO
      }
    });
    return list;
  }

  private static void forEachInterval(TimeBarModel model, Consumer<Interval> consumer) {
    for (int r = 0; r < model.getRowCount(); r++) {
      TimeBarRow row = model.getRow(r);
      Iterator it = row.getIntervals().iterator();
      while (it.hasNext()) {
        Interval interval = (Interval) it.next();
        consumer.accept(interval);
      }
    }
  }

  private static double getIntervalSum(TimeBarRow row) {
    double result = 0;
    Iterator it = row.getIntervals().iterator();
    while (it.hasNext()) {
      Interval interval = (Interval) it.next();
      result += interval.getEnd().diffMinutes(interval.getBegin());
    }

    return result;
  }

  public void add(Video v) {
    Timeline t = new Timeline(v);
    timelines.add(t);
    this.emit("timeline:new", t);
  }

  public void remove(final Timeline timeline) {
    if (timeline == null) {
      throw new NullPointerException();
    }

    this.timelines.remove(timeline);
    this.emit("timelines:changed", timeline);
  }

  public void clear() {
    if (this.timelines.isEmpty()) {
      return;
    }

    this.timelines.clear();
    this.emit("timelines:changed");
  }
}
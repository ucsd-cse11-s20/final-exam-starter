import tester.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class ImageData {
  String keywords; // All the keywords, separated by spaces
  String filetype; // .gif, .png, .jpg, and so on
  int width;
  int height;
  ImageData(String keywords, String filetype, int width, int height) {
    this.keywords = keywords;
    this.filetype = filetype;
    this.width = width;
    this.height = height;
  }
}

interface ImageQuery {
  // EXAM: Add new methods here
  boolean matches(ImageData id);
  ImageQuery and(ImageQuery other);
  ImageQuery or(ImageQuery other);
}

abstract class AQuery implements ImageQuery {
  public ImageQuery and(ImageQuery other) {
    return new AndQuery(this, other);
  }
  public ImageQuery or(ImageQuery other) {
    return new OrQuery(this, other);
  }
}

abstract class AComboQuery extends AQuery {
  ImageQuery iq1, iq2;
  AComboQuery(ImageQuery iq1, ImageQuery iq2) {
    this.iq1 = iq1;
    this.iq2 = iq2;
  }
}

class ContainsKeyword extends AQuery {
  String keyword;
  ContainsKeyword(String keyword) {
    this.keyword = keyword;
  }
  public boolean matches(ImageData id) {
    return id.keywords.indexOf(this.keyword) != -1;
  }
}

class LargerThan extends AQuery {
  int minWidth, minHeight;
  LargerThan(int minWidth, int minHeight) {
    this.minWidth = minWidth;
    this.minHeight = minHeight;
  }
  public boolean matches(ImageData id) {
    return id.width >= this.minWidth && id.height >= this.minHeight;
  }
}

class MatchesExtension extends AQuery {
  String ext;
  MatchesExtension(String ext) { this.ext = ext; }
  public boolean matches(ImageData id) {
    return id.filetype.equals(this.ext);
  }
}

class AndQuery extends AComboQuery {
  AndQuery(ImageQuery iq1, ImageQuery iq2) {
    super(iq1, iq2);
  }
  public boolean matches(ImageData id) {
    return this.iq1.matches(id) && this.iq2.matches(id);
  }
}

class OrQuery extends AComboQuery {
  OrQuery(ImageQuery iq1, ImageQuery iq2) {
    super(iq1, iq2);
  }
  public boolean matches(ImageData id) {
    return this.iq1.matches(id) || this.iq2.matches(id);
  }
}

class NotQuery extends AQuery {
  ImageQuery iq;
  NotQuery(ImageQuery iq) {
    this.iq = iq;
  }
  public boolean matches(ImageData id) {
    return !this.iq.matches(id);
  }
}

class ExamplesSearch {
  // EXAM: Write examples/tests here
}

import {Component, ViewChild} from '@angular/core';
import {TagComponent} from "./component/tag/tag.component";
import {ThumbComponent} from "./component/thumb/thumb.component";
import {CardComponent} from "./component/card/card.component";
import {RandomizerUtil} from "./core/util/randomizer.util";
import {ViewData} from "./core/data/view.data";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'bridge-pattern-app';
  @ViewChild(TagComponent, { static: false }) tagView!: TagComponent;
  @ViewChild(ThumbComponent, { static: false }) thumbView!: ThumbComponent;
  @ViewChild(CardComponent, { static: false }) cardView!: CardComponent;

  getResource = (viewType: string): ViewData => {
    return RandomizerUtil.getResource(viewType);
  }


  updateViews = () => {
    this.tagView.update();
    this.cardView.update();
    this.thumbView.update();
  }
}

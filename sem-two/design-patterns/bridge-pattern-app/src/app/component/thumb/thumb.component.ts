import {Component, Input, OnInit} from '@angular/core';
import {ViewData} from "../../core/data/view.data";
import {RandomizerUtil} from "../../core/util/randomizer.util";

@Component({
  selector: 'app-thumb',
  templateUrl: './thumb.component.html',
  styleUrls: ['./thumb.component.css']
})
export class ThumbComponent implements OnInit {
  @Input() viewModel!: ViewData;

  constructor() {
  }

  ngOnInit(): void {
  }
  update = () => {
    this.viewModel = RandomizerUtil.getResource(ThumbComponent.name);
  }
}

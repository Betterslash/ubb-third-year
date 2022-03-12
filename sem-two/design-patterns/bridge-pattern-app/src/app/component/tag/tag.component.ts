import {Component, Input, OnInit} from '@angular/core';
import {ViewData} from "../../core/data/view.data";
import {RandomizerUtil} from "../../core/util/randomizer.util";

@Component({
  selector: 'app-tag',
  templateUrl: './tag.component.html',
  styleUrls: ['./tag.component.css']
})
export class TagComponent implements OnInit {
  @Input() viewModel!: ViewData;

  constructor() {
  }

  ngOnInit(): void {
  }
  update = () => {
    this.viewModel = RandomizerUtil.getResource(TagComponent.name);
  }
}

import {Component, Input, OnInit} from '@angular/core';
import {ViewData} from "../../core/data/view.data";
import {RandomizerUtil} from "../../core/util/randomizer.util";

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent  implements OnInit{
  @Input() viewModel!: ViewData;

  constructor() {
  }

  ngOnInit(): void {
  }

  update = () => {
    this.viewModel = RandomizerUtil.getResource(CardComponent.name);
  }
}

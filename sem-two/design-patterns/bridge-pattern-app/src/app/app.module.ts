import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ThumbComponent } from './component/thumb/thumb.component';
import { CardComponent } from './component/card/card.component';
import { TagComponent } from './component/tag/tag.component';

@NgModule({
  declarations: [
    AppComponent,
    ThumbComponent,
    CardComponent,
    TagComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

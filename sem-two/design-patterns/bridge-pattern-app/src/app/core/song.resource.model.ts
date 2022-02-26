import {ResourceModel} from "./resource.model";
import {SongModel} from "./song.model";

export class SongResourceModel implements ResourceModel{

  private readonly song: SongModel;

  getHeader(): string {
    return `${this.song.artist} - ${this.song.artist}`;
  }

  getId(): string {
    return `${this.song.id}`;
  }

  getSnippet(): string {
    return this.song.album !== 'Single' ? `Album which the song belongs to ${this.song.album}. Genre of the song is ${this.song.genre}`
      : `Single song. Gnere of the song is ${this.song.genre}`;
  }

  constructor(song: SongModel) {
    this.song = song;
  }

}

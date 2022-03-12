import {moviesData} from "../data/movie.data";
import {ResourceModel} from "../bridge-pattern/resource/resource.model";
import {MovieResourceModel} from "../bridge-pattern/resource/movie.resource.model";
import {SongResourceModel} from "../bridge-pattern/resource/song.resource.model";
import {ArtistResourceModel} from "../bridge-pattern/resource/artist.resource.model";
import {songsData} from "../data/song.data";
import {artistsData} from "../data/artist.data";
import {ViewData} from "../data/view.data";
import {TagComponent} from "../../component/tag/tag.component";
import {TagViewModel} from "../bridge-pattern/bridge/tag.view.model";
import {ThumbComponent} from "../../component/thumb/thumb.component";
import {ThumbViewModel} from "../bridge-pattern/bridge/thumb.view.model";
import {CardComponent} from "../../component/card/card.component";
import {CardViewModel} from "../bridge-pattern/bridge/card.view.model";

export class RandomizerUtil{
  private static getRandoms = (): number => {
      return Math.floor(Math.random() * 3);
  }

  private static getRandomGenre = (): number => {
    return Math.floor(Math.random() * 3);
  }

  static getRepresentation = (): ResourceModel => {
    const resourceIndex = RandomizerUtil.getRandoms();
    const genreIndex = RandomizerUtil.getRandomGenre();
    switch (genreIndex){
      case 0: return new MovieResourceModel(moviesData[resourceIndex]);
      case 1: return new ArtistResourceModel(artistsData[resourceIndex]);
      case 2: return new SongResourceModel(songsData[resourceIndex]);
      default: throw new Error('Couldnt generate data...');
    }
  }

  private static getRandomResource = () : ResourceModel=> {
    return RandomizerUtil.getRepresentation();
  }

  static getResource = (viewType : string): ViewData => {
    const resource = this.getRandomResource();
    switch (viewType){
      case TagComponent.name: {
        return new TagViewModel(resource).getViewData();
      }
      case ThumbComponent.name : {
        return new ThumbViewModel(resource).getViewData();
      }
      case CardComponent.name : {
        return new CardViewModel(resource).getViewData();
      }
      default : throw new Error(`Element ${viewType} has no registered view Type !!`);
    }
  }
}

import {BridgeViewModel} from "../bridge.view.model";
import {moviesData} from "../data/movie.data";
import {ResourceModel} from "../resource.model";
import {MovieResourceModel} from "../movie.resource.model";
import {SongResourceModel} from "../song.resource.model";
import {ArtistResourceModel} from "../artist.resource.model";
import {songsData} from "../data/song.data";
import {artistsData} from "../data/artist.data";

export class RandomizerUtil{
  private static getRandoms = (): number => {
      return Math.floor(Math.random() * 3);
  }

  private static getRandomGenre = (): number => {
    return Math.floor(Math.random() * 2);
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
}

import {ResourceModel} from "./resource.model";
import {MovieModel} from "./movie.model";

export class MovieResourceModel implements ResourceModel {

  private movie: MovieModel;

  getHeader(): string {
    return this.movie.title;
  }

  getId(): string {
    return this.movie.id;
  }

  getSnippet(): string {
    return `${this.movie.description}\n Genre of the movie : ${this.movie.genre} `;
  }

  constructor(movie: MovieModel) {
    this.movie = movie;
  }

}

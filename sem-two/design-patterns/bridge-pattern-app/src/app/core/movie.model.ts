import {Guid} from "./util/guid.util";

export class MovieModel{
  private readonly _id: string;
  private readonly _title: string;
  private readonly _description: string;
  private readonly _genre: string;

  constructor(title: string, description: string, genre: string) {
    this._id = Guid.newGuid();
    this._title = title;
    this._description = description;
    this._genre = genre;
  }

  get id(): string {
    return this._id;
  }

  get title(): string {
    return this._title;
  }

  get description(): string {
    return this._description;
  }

  get genre(): string {
    return this._genre;
  }
}

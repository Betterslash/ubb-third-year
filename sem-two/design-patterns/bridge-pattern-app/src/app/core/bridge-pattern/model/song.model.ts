import {Guid} from "../../util/guid.util";

export class SongModel{
  private readonly _id: string;
  private readonly _artist: string;
  private readonly _name: string;
  private readonly _genre: string;
  private readonly _album?: string;

  constructor(artist: string, name: string, genre: string, album: string) {
    this._id = Guid.newGuid();
    this._artist = artist;
    this._name = name;
    this._genre = genre;
    this._album = album;
  }

  get id(): string {
    return this._id;
  }

  get artist(): string {
    return this._artist;
  }

  get name(): string {
    return this._name;
  }

  get genre(): string {
    return this._genre;
  }

  get album(): string {
    return this._album || 'Single';
  }
}

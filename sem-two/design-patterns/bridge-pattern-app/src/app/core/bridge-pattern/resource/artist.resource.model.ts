import {ResourceModel} from "./resource.model";
import {ArtistModel} from "../model/artist.model";

export class ArtistResourceModel implements ResourceModel {

    private artist: ArtistModel;

    constructor(artist: ArtistModel) {
        this.artist = artist;
    }

    getId(): string {
        return this.artist.id;
    }

    getSnippet(): string {
        return this.artist.bio;
    }

    getHeader(): string {
        return this.artist.name;
    }
}

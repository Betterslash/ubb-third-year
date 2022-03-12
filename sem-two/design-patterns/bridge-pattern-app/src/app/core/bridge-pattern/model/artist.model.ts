import {Guid} from "../../util/guid.util";

export class ArtistModel {
    private readonly _id: string;
    private readonly _name: string;
    private readonly _age: number;
    private readonly _bio: string;

    constructor(name: string, age: number, bio: string) {
        this._id = Guid.newGuid();
        this._name = name;
        this._age = age;
        this._bio = bio;
    }

    get id(): string {
        return this._id;
    }

    get name(): string {
        return this._name;
    }

    get age(): number {
        return this._age;
    }

    get bio(): string {
        return this._bio;
    }

}

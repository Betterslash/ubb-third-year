export interface AnimalModel{
    id: string;
    name: string,
    race: string,
    age: string,
    owner: string,
    imageUrls: string[]
}

export interface CatModel extends AnimalModel{
    furColor: string,
    favouriteFood: string
}

export interface DogModel extends AnimalModel{
    furColor: string,
    weight: number,
    height: number
}
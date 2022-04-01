import React from "react";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import {AnimalModel} from "../core/model/AnimalModel";
import {AddCat, AddDog, Context, Strategy} from "../infrastructure/strategy/Context";

export const AddAnimalComponent: React.FC = () => {
    const animal = {race: '0'} as AnimalModel;
    const context: Context = new Context({} as Strategy);
    let animalType = '0';
    const applyStrategy = (): void => {
        if (animalType === '0') {
            context.setStrategy(new AddDog());
        } else if (animalType === '1') {
            context.setStrategy(new AddCat());
        }
        context.execute(animal)
            .then((response) => {console.log(response.data);});
    }
    return (
        <Container>
            <br/>
            <h2>
                Add Animal
            </h2>
            <Form>
                <FormGroup>
                    <Label for={"selectRace"}>Race</Label>
                    <Input
                        id="typeInput"
                        name="selectType"
                        onInput={(e) => animalType = e.currentTarget.value}
                        type="select">
                        <option value={"0"}>
                            Dog
                        </option>
                        <option value={"1"}>
                            Cat
                        </option>
                    </Input>
                </FormGroup>
                <FormGroup>
                    <Label for={"raceInput"}>Race</Label>
                    <Input
                        onInput={(e) => animal.race = e.currentTarget.value}
                        name={"raceInput"}
                        type={"text"}/>
                </FormGroup>
                <FormGroup>
                    <Label for={"nameInput"}>Name</Label>
                    <Input
                        onInput={(e) => animal.name = e.currentTarget.value}
                        name={"nameInput"}
                        type={"text"}/>
                </FormGroup>
                <FormGroup>
                    <Label for={"ageInput"}>Age</Label>
                    <Input type={"number"}
                           onInput={(e) => animal.age = e.currentTarget.value}
                    name={"numberInput"}/>
                </FormGroup>
                <Button
                    color={"primary"}
                    onClick={applyStrategy}>Add +</Button>
            </Form>
        </Container>
    );
}
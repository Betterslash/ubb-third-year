import React from "react";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";

export const AddAnimalComponent: React.FC = () => {
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
                        id="reaceInput"
                        name="selectRace"
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
                    <Label for={"nameInput"}>Name</Label>
                    <Input
                        name={"nameInput"}
                        type={"text"}/>
                </FormGroup>
                <FormGroup>
                    <Label for={"ageInput"}>Age</Label>
                    <Input type={"number"}
                    name={"numberInput"}/>
                </FormGroup>
                <Button color={"primary"}>Add +</Button>
            </Form>
        </Container>
    );
}
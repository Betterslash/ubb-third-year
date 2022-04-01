import React, {useEffect, useState} from "react";
import {CatService} from "../infrastructure/services/CatService";
import {DogService} from "../infrastructure/services/DogService";
import {Container, Spinner, Table} from "reactstrap";
import {TableView} from "../infrastructure/bridge/CustomViewModel";
import {useNavigate} from "react-router-dom";
import {AnimalMap} from "../infrastructure/utils/AnimalMap";

export const ManagePetsComponent: React.FC = () => {
    const [isProcessing, setIsProcessing] = useState(false);
    const [animalsMap, setAnimalsMap] = useState<AnimalMap>();

    const navigate = useNavigate();

    useEffect(() => {
        setIsProcessing(true);
        CatService.getAll()
            .then((catsResponse) => {
                DogService.getAll().then((dogsResponse) => {
                    const animalMap = new AnimalMap(catsResponse.data, dogsResponse.data);
                    setAnimalsMap(animalMap);
                    setIsProcessing(false);
                });
            });
    }, [])

    return (
        <Container>
            <br/>
            <Table bordered>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Race</th>
                    <th>Age</th>
                </tr>
                </thead>
                <tbody>
                {animalsMap && animalsMap.getList()
                    .map(e => new TableView(e))
                    .map((e, index) =>
                        <tr key={index.toString()}
                            onClick={() => {
                                navigate(`/view/${e.animal.id}`)
                            }}>
                            <th scope={"row"}>{index + 1}</th>
                            <th>{e.name}</th>
                            <th>{e.race}</th>
                            <th>{e.age}</th>
                        </tr>)}
                </tbody>
            </Table>
            <Spinner hidden={!isProcessing}>
                Loading...
            </Spinner>
        </Container>
    );
}
import {Environment} from "../../environment/Environment";
import {AxiosResponse} from "axios";
import {InventoryItem} from "../model/inventory-item.model";
import ApiService from "./api.service";

class ItemInventoryService{
    private static readonly ITEM_INVENTORY_URL : string = `${Environment.apiUrl}item`;

    getAll = () : Promise<AxiosResponse<InventoryItem[]>> => {
        return ApiService.get<InventoryItem[]>(ItemInventoryService.ITEM_INVENTORY_URL);
    }

}

export default new ItemInventoryService();


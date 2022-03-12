import {ResourceModel} from "../resource/resource.model";
import {ViewData} from "../../data/view.data";

export abstract class BridgeViewModel {
  protected resource: ResourceModel;

  public abstract getViewData(): ViewData;

  constructor(resource: ResourceModel) {
    this.resource = resource;
  }
}


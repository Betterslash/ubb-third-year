import {ResourceModel} from "./resource.model";
import {ViewData} from "./util/view.data";

export abstract class BridgeViewModel {
  protected resource: ResourceModel;

  protected abstract getViewData(): ViewData;

  constructor(resource: ResourceModel) {
    this.resource = resource;
  }
}


import {BridgeViewModel} from "./bridge.view.model";
import {ViewData} from "../../data/view.data";

export class TagViewModel extends BridgeViewModel {
    getViewData(): ViewData {
      return {
        header: this.resource.getHeader()
      } as ViewData;
    }
}

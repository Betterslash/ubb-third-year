import {BridgeViewModel} from "./bridge.view.model";
import {ViewData} from "./util/view.data";

export class TagViewModel extends BridgeViewModel {
    protected getViewData(): ViewData {
      return {
        header: this.resource.getHeader()
      } as ViewData;
    }
}

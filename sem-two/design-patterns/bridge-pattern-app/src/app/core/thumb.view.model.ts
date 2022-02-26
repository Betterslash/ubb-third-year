import {BridgeViewModel} from "./bridge.view.model";
import {ViewData} from "./util/view.data";

export class ThumbViewModel extends BridgeViewModel {
    protected getViewData(): ViewData {
      return {
        id: this.resource.getId(),
        header: this.resource.getHeader()
      } as ViewData;
    }

}

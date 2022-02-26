import {BridgeViewModel} from "./bridge.view.model";
import {ViewData} from "./util/view.data";

export class CardViewModel extends BridgeViewModel {
    protected getViewData(): ViewData {
      return {
        id: this.resource.getId(),
        header: this.resource.getHeader(),
        body: this.resource.getSnippet()
      } as ViewData;
    }

}

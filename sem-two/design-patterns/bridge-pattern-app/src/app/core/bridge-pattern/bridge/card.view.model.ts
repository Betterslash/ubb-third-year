import {BridgeViewModel} from "./bridge.view.model";
import {ViewData} from "../../data/view.data";

export class CardViewModel extends BridgeViewModel {
  getViewData(): ViewData {
      return {
        id: this.resource.getId(),
        header: this.resource.getHeader(),
        body: this.resource.getSnippet()
      } as ViewData;
    }

}

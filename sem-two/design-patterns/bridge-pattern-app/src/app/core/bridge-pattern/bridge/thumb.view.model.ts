import {BridgeViewModel} from "./bridge.view.model";
import {ViewData} from "../../data/view.data";

export class ThumbViewModel extends BridgeViewModel {
  getViewData(): ViewData {
      return {
        header: this.resource.getHeader(),
        body: this.resource.getSnippet()
      } as ViewData;
    }

}

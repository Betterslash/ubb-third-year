import {Animation, createAnimation} from "@ionic/react";

export const CustomAnimation = () : Animation => {
    return createAnimation('my-animation-identifier')
        // @ts-ignore
        .addElement(document.querySelector(`#item-${id}`))
        .duration(100)
        .keyframes([
            {offset: 0, background: `red`},
            {offset: 0.72, background: 'var(--background)'},
            {offset: 1, background: 'blue'}
        ]);
}

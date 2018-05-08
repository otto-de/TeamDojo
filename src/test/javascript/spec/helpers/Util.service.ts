class Util {
    wrap(instance: object, properties: object, callback: () => void = undefined): object {
        return function() {
            for (const key in properties) {
                if (properties.hasOwnProperty(key)) {
                    this[key] = properties[key];
                }
            }
            if (callback) {
                callback();
            }
            return this;
        }.apply(instance);
    }
}

export default new Util();

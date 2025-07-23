# iso-languages
iso-languages is a Clojure library for ISO 639 language code lookup and detection.

Provides functions to retrieve language information by ISO 639-1 (alpha-2), ISO 639-2 (alpha-3) codes, or language names, and to detect standardized language keywords from various input formats.


## installation
Add iso-languages to dependency list

```clojure
[org.clojars.jj/iso-languages "1.0.0-SNAPSHOT"]
```

## Usage

```clojure
(:require [jj.iso.languages :as iso-languages])

(iso-languages/get-language :chuvash) ;; returns {:alpha2   "cv" :alpha3-b "chv" :english  "Chuvash" :french   "Tchouvache"}
```


## License

Copyright © 2025 [ruroru](https://github.com/ruroru)

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
https://www.eclipse.org/legal/epl-2.0/.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

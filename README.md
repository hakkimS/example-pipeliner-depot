# Example Pipeliner Depot

This depot consists of example pipelines and stages to showcase how to utilize
the [Pipeliner](https://github.com/Daimler/pipeliner) library to be able to
write consistent Pipelines in Jenkins which are able to reuse code and
introduce structure in big projects.

## How to build

1. `git clone https://github.com/jeena/example-pipeliner-depot`
2. `git submodule update --init`
3. `./gradlew build`
4. Optional: `./gradlew test`

## How to integrate into Jenkins

The example-pipeliner-depot should be added to Jenkins as a global shared
pipeline library so that it can be used by all Jenkinsfiles.

To add it you need to have administrator rights and then go to:

- Manage Jenkins
- Configure System
- Global Pipeline Libraries

And then set up as below:

| Field                                          | Value                   |
| ---------------------------------------------- | ----------------------- |
| Name                                           | example-pipeliner-depot |
| Default version                                | master                  |
| Allow default version to be overridden         | Checked                 |
| Include @Library changes in job recent changes | Checked                 |

Under `Retrival method` choose `Modern SCM` point to this repository.

For more information about that precedure please see [Using libraries in the
Jenkins Documentation](https://www.jenkins.io/doc/book/pipeline/shared-libraries/#using-libraries).

## License

Copyright 2020 Jeena

example-pipeliner-depot is free software: you can redistribute it and/or
modify it under the terms of the GNU Affero General Public License published
by the Free Software Foundation, either version 3 of the License, or (at your
option) any later version.

example-pipeliner-depot is distributed in the hope that it will be useful, bu
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
details.

You should have received a copy of the GNU General Public License along with
example-pipeliner-depot. If not, see http://www.gnu.org/licenses/.

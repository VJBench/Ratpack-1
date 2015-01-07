/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.test;

import ratpack.func.Action;
import ratpack.test.http.TestHttpClient;

public interface CloseableApplicationUnderTest extends ApplicationUnderTest, AutoCloseable {

  /**
   * Provides the given action with a {@link #getHttpClient() test http client} for this application, then closes this application.
   * <p>
   * The application will be closed regardless of whether the given action throws an exception.
   * <pre class="java">{@code
   *
   * import ratpack.test.embed.EmbeddedApp;
   *
   * public class Example {
   *   public static void main(String... args) throws Exception {
   *     EmbeddedApp.fromHandler(ctx -> ctx.render("ok"))
   *       .test(httpClient -> {
   *         assert httpClient.get().getBody().getText().equals("ok");
   *       });
   *   }
   * }
   * }</pre>
   *
   * @param action an action that tests this embedded application
   * @throws Exception any thrown by {@code action}
   */
  default void test(Action<? super TestHttpClient> action) throws Exception {
    try {
      action.execute(getHttpClient());
    } finally {
      close();
    }
  }

  @Override
  void close();

}

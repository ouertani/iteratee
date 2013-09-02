/**
 * 
 * Only a fool tries to implement the famous {@code iteratees} of Haskell in java.
 * And that gave me this idea.
 * 
 * <p> {@code Iteratees} in haskell are intended to be &quot;efficient, predictable and safe&quot; alternatives
 * to lazy I/O. Read about the perils of lazyIO and the need for iteratee at  Oleg's page
 * </p>
 * <h3>Notes about implementation in this library</h3>
 * <p>
 *  Where Oleg uses lists as examples, this library uses {@link java.nio.Buffer buffers}. Using lists in java seems extremely 
 * pointless. So one can perhaps take the following route:
 * <ol>
 * <li>start with {@link haskell.data.enumerator.Stream Stream interface} and look at its two implementations.</li>
 * <li>Go over to {@link haskell.data.enumerator.Step Step} interface
 *  <ul> The following classes provide concrete implementations
 *      <li>{@link haskell.data.enumerator.ex.PeekStep PeekStep}</li>
 *      <li>{@link haskell.data.enumerator.ex.HeadStep HeadStep}</li>
 *       <li>{@link haskell.data.enumerator.ex.BreakStep BreakStep}</li>
 *       <li>{@link haskell.data.enumerator.ex.HeadsStep HeadsStep}</li>
 *  </ul>
 *  <li> Move on to {@link haskell.data.enumerator.Iteratee Iteratee} and its implementation</li>
 *  <li> {@link haskell.data.enumerator.BindIteratee BindIteratee} and {@link haskell.data.enumerator.RetIteratee RetIteratee} to 
 * understand how iteratees compose </li>
 * <li> {@link haskell.data.enumerator.IterEnumerator Enumerator}</li>
 *  <li>{@link haskell.data.enumerator.IterEnumeratee Enumeratee}</li>
 * </li>
 * 
 * <br/>
 * The {@link haskell.data.enumerator.ex ex package} implements examples given in Oleg's talk notes. Finally the junits might 
 * provide additional information.
 * </p>
 * <h3>Disclaimer</h3>
 * <p> This java library is a strictly reference implementation. There are so many ridiculous design decisions,
 * type-safety issues, bugs, germs, bacteria and tyrannosaurs that its very existence is almost pointless.</p>
 * <p> Almost.
 * It has but one function,at least the author deludes himself in believing so. Namely, to give an intuitive idea of the 
 * magic of {@code iteratees} for people like the author (brought up in java, migrated to
 * Haskell Land,  waiting for the green card, while flipping burgers). 
 * </p>
 
 * <p><b>THIS LIBRARY IS NOT FOR YOU IF </b>
 * <ul>
 * <li>you do not know java.</li>
 * <li>you are already a haskell guru who understands {@code iteratees} left to right, top to bottom and front to back. Especially if
 * you have a weak heart or something. In the interests of explanation, I made many design decisions that might 
 * hurt your sensibilities.</li>
 * <li>If, for some strange reason, you want <b>real</b> java implementation of {@code Iteratee}. This library hasn't been tested.</li>
 * </ul>
 * 
 * @see http://okmij.org/ftp/Streams.html#iteratee
 * @see http://hackage.haskell.org/package/enumerator-0.4.18
 */
package haskell.data.enumerator;

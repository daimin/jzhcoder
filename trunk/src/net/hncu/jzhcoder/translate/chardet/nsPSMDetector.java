/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is mozilla.org code.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by the Initial Developer are Copyright (C) 1998
 * the Initial Developer. All Rights Reserved.
 *
 * These codes from jchardet,we just modify a little.
 * 
 * jchardet is a java port of the source from mozilla's automatic charset detection algorithm. 
 * The original author is Frank Tang. What is available here is the java port of that code. 
 * The original source in C++ can be found from 
 * http://lxr.mozilla.org/mozilla/source/intl/chardet/ 
 * More information can be found at 
 * http://www.mozilla.org/projects/intl/chardet.html
 * All Rights Reserved. 
 * 
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */
package net.hncu.jzhcoder.translate.chardet;


public  abstract class nsPSMDetector {

	   public static final int SIMPLIFIED_CHINESE  =  1 ;

	   public static final int NO_OF_LANGUAGES     =  2 ;
	   public static final int MAX_VERIFIERS       = 16 ;

	   public static final int ALL = 0;

	   nsVerifier[] verifiers ;
	   nsEUCStatistics[] mStatisticsData ;

	   nsEUCSampler	mSampler = new nsEUCSampler() ;
	   byte[]    mState = new byte[MAX_VERIFIERS] ;
	   int[]     mItemIdx = new int[MAX_VERIFIERS] ;

	   int     mItems ;
	   int	   mClassItems ;
	 
	   boolean mDone ;
	   boolean mRunSampler ;
	   boolean mClassRunSampler ;

	   public nsPSMDetector() {
		initVerifiers( nsPSMDetector.SIMPLIFIED_CHINESE );
		Reset() ;
	   }

	   public nsPSMDetector(int langFlag) {
		initVerifiers(langFlag);
		Reset() ;
	   }

	   public nsPSMDetector(int aItems, nsVerifier[] aVerifierSet, 
						nsEUCStatistics[] aStatisticsSet)  {
		mClassRunSampler = ( aStatisticsSet != null ) ;
		mStatisticsData = aStatisticsSet ;
		verifiers = aVerifierSet ;

		mClassItems = aItems ;
		Reset() ;
	   }
	   

	   public void Reset() {
		mRunSampler = mClassRunSampler ;
		mDone = false ;
		mItems = mClassItems ;

		for(int i=0; i<mItems; i++) {
		   mState[i] = 0;
		   mItemIdx[i] = i;
		}

		mSampler.Reset() ;
	   }

	   protected void initVerifiers(int currVerSet) {

	        int currVerifierSet ;

		if (currVerSet == nsPSMDetector.SIMPLIFIED_CHINESE ) {
		   currVerifierSet = currVerSet ;
		}
		else {
		   throw new RuntimeException("Charset which be detected must be simplified chinese!");
		}

		verifiers = null ;
		mStatisticsData = null ;



		//==========================================================
		if ( currVerifierSet == nsPSMDetector.SIMPLIFIED_CHINESE ) {

		   verifiers = new nsVerifier[] {
	      		new nsUTF8Verifier(),
	      		new nsGB2312Verifier(),
	      		new nsGB18030Verifier(),
	      		new nsISO2022CNVerifier(),
	      		new nsHZVerifier(),
	      		new nsCP1252Verifier(),
	      		new nsUCS2BEVerifier(),
	      		new nsUCS2LEVerifier()
		   };
		}


		mClassRunSampler = ( mStatisticsData != null ) ;
	       	mClassItems = verifiers.length ;

	   }
		  
	   public abstract void Report(String charset) ;

	   public boolean HandleData(byte[] aBuf, int len) {


		int i,j;
		byte b, st;

	 	for( i=0; i < len; i++) {
		   b = aBuf[i] ;

		   for (j=0; j < mItems; )
		   {
			st = nsVerifier.getNextState( verifiers[mItemIdx[j]], 
							b, mState[j]) ;
	//if (st != 0)
	//System.out.println( "state(0x" + Integer.toHexString(0xFF&b) +") =>"+ Integer.toHexString(st&0xFF)+ " " + mVerifier[mItemIdx[j]].charset());

			if (st == nsVerifier.eItsMe) {

	//System.out.println( "eItsMe(0x" + Integer.toHexString(0xFF&b) +") =>"+ mVerifier[mItemIdx[j]].charset());

			   Report( verifiers[mItemIdx[j]].charset() );
			   mDone = true ;
			   return mDone ;

		        } else if (st == nsVerifier.eError ) {

	//System.out.println( "eNotMe(0x" + Integer.toHexString(0xFF&b) +") =>"+ mVerifier[mItemIdx[j]].charset());
			   mItems--;
			   if (j < mItems ) {
				mItemIdx[j] = mItemIdx[mItems];	
				mState[j]   = mState[mItems];
			   }

			} else {
			  
			    mState[j++] = st ;

			}
		   }

		   if ( mItems <= 1 ) {

		        if( 1 == mItems) {
			   Report( verifiers[mItemIdx[0]].charset() );
			}
			mDone = true ;
			return mDone ;

		   } 
		   else {
			
			int nonUCS2Num=0;
			int nonUCS2Idx=0;

			for(j=0; j<mItems; j++) {
			   if ( (!(verifiers[mItemIdx[j]].isUCS2())) &&
				(!(verifiers[mItemIdx[j]].isUCS2())) ) 
			   {
				nonUCS2Num++ ;
				nonUCS2Idx = j ;
			   }
			}

			if (1 == nonUCS2Num) {
			   Report( verifiers[mItemIdx[nonUCS2Idx]].charset() );
			   mDone = true ;
			   return mDone ;
			}
		   }


	       } // End of for( i=0; i < len ...

	       if (mRunSampler)
		  Sample(aBuf, len);

	       return mDone ;
	   }


	   public void DataEnd() {
		
		if (mDone == true)
		    return ;

		if (mItems == 2) {
		    if ((verifiers[mItemIdx[0]].charset()).equals("GB18030")) {
			Report(verifiers[mItemIdx[1]].charset()) ;
			mDone = true ;
		    } else if ((verifiers[mItemIdx[1]].charset()).equals("GB18030")) {
			Report(verifiers[mItemIdx[0]].charset()) ;
			mDone = true ;
		    }
		}

		if (mRunSampler)
		   Sample(null, 0, true);
	   }

	   public void Sample(byte[] aBuf, int aLen) {
		  Sample(aBuf, aLen, false) ;
	   }

	   public void Sample(byte[] aBuf, int aLen, boolean aLastChance)
	   {
	      	int possibleCandidateNum  = 0;
		int j;
		int eucNum=0 ;

		for (j=0; j< mItems; j++) {
		   if (null != mStatisticsData[mItemIdx[j]]) 
			eucNum++ ;
		   if ((!verifiers[mItemIdx[j]].isUCS2()) && 
				(!(verifiers[mItemIdx[j]].charset()).equals("GB18030")))
			possibleCandidateNum++ ;
		}

		mRunSampler = (eucNum > 1) ;
		
	     	if (mRunSampler) {
	            mRunSampler = mSampler.Sample(aBuf, aLen);
	            if(((aLastChance && mSampler.GetSomeData()) || 
	                mSampler.EnoughData())
	               && (eucNum == possibleCandidateNum)) {
	              mSampler.CalFreq();

	              int bestIdx = -1;
	              int eucCnt=0;
	              float bestScore = 0.0f;
	              for(j = 0; j < mItems; j++) {
	                 if((null != mStatisticsData[mItemIdx[j]])  &&
	                   (!(verifiers[mItemIdx[j]].charset()).equals("Big5")))
	                 {
	                    float score = mSampler.GetScore(
	                       mStatisticsData[mItemIdx[j]].mFirstByteFreq(),
	                       mStatisticsData[mItemIdx[j]].mFirstByteWeight(),
	                       mStatisticsData[mItemIdx[j]].mSecondByteFreq(),
	                       mStatisticsData[mItemIdx[j]].mSecondByteWeight() );
	//System.out.println("FequencyScore("+mVerifier[mItemIdx[j]].charset()+")= "+ score);
	                    if(( 0 == eucCnt++) || (bestScore > score )) {
	                       bestScore = score;
	                       bestIdx = j;
	                    } // if(( 0 == eucCnt++) || (bestScore > score )) 
	                } // if(null != ...)
	             } // for
	             if (bestIdx >= 0)
	             {
	               Report( verifiers[mItemIdx[bestIdx]].charset());
	               mDone = true;
	             }
	           } // if (eucNum == possibleCandidateNum)
	         } // if(mRunSampler)
	   }

	   public String[] getProbableCharsets() {

		if (mItems <= 0) {
		   String[] nomatch = new String[1];
		   nomatch[0] = "nomatch" ;
		   return nomatch ;
		}

		String ret[] = new String[mItems] ;
		for (int i=0; i<mItems; i++)
			ret[i] = verifiers[mItemIdx[i]].charset() ;
		return ret ;
	   }
}
